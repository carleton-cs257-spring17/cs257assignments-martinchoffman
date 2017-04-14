
'''
    spotipy_test.py
    Chris Tordi and Martin Hoffman 

    Python Program that uses spotipy to retrieve data from an HTTP-based Spotify API.
    Parses results and returns information based on function call

    USAGE STATEMENT: 1) arg1: getAlbums, arg2: name of artist
                     2) arg1: getTracks, arg2: name of album
                     3) arg1: playTrack, arg2: name of track

'''

import sys

try:
    import spotipy
except ImportError:
    import pip
    pip.main(['install', 'spotipy'])
    exit('Spotipy dependency installed!\nPlease rerun command...')

import webbrowser


def main():
    spotify = spotipy.Spotify()
    fnct = sys.argv[1]

    if fnct == 'getAlbums':
        artist = sys.argv[2]
        get_artist_uri(spotify, artist)
    elif fnct == 'getTracks':
        album = sys.argv[2]
        get_album_uri(spotify, album)
    elif fnct == 'playTrack':
        track = sys.argv[2]
        playTrack(spotify, track)
    else:
        print('Usage: getAlbums ARTIST_NAME or getTracks ALBUM_NAME')

'''
    Param: spotify object, string "artist" of artist you want to find albums for
    Returns: All albums done by "artist"
    
'''
def get_artist_uri(spotify, artist): # artist isn't used because the first line accesses it directly via sys.argv[2:]
    artist = ' '.join(sys.argv[2:])
    results = spotify.search(q='artist:' + artist, type='artist')
    uri = results['artists']['items'][0]['uri']

    results = spotify.artist_albums(uri, album_type='album')
    albums = results['items']
    while results['next']:
        results = spotify.next(results)
        albums.extend(results['items'])

    for album in albums:
        print(album['name'])

'''
    Param: spotify object, string "album" - name of album you would like tracks for
    Return: Tracks in album

    Program takes album string and prints tracks from that album
'''
def get_album_uri(spotify, album):
    album = ' '.join(sys.argv[2:])
    results = spotify.search(q='album:' + album, type='album')
    uri = results['albums']['items'][0]['uri']

    results = spotify.album_tracks(uri)
    albums = results['items']
    while results['next']:
        results = spotify.next(results)
        albums.extend(results['items'])

    for album in albums:
        print(album['name'])
'''
    Param: spotify object, string "track" - name of track you would like to play
    Returns: opens web browser and plays 30 sec preview of track
'''
def playTrack(spotify, track):
    track = ' '.join(sys.argv[2:])
    results = spotify.search(q='track:' + track, type='track')
    audio_preview = results['tracks']['items'][0]['preview_url']
    audio = results['tracks']['items'][0]['external_urls']
    audio = audio['spotify']
    webbrowser.open(audio_preview)
    #webbrowser.open(audio)


if __name__ == '__main__':
    main()