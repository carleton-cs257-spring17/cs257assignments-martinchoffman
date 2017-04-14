# Client ID:        771fca1e6b1649e48b00189968912e6a
# Client Secret:    4343723038b44a27aba4372d48e8fa7d

import spotipy
import sys
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





