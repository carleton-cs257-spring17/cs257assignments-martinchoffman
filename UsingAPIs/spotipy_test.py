# Client ID:        771fca1e6b1649e48b00189968912e6a
# Client Secret:    4343723038b44a27aba4372d48e8fa7d

import spotipy
import sys

def main():
    spotify = spotipy.Spotify()
    function = sys.argv[1]

    #results = spotify.search(q='album: Graduation', type='album')
    #print(results)
    #uri = results['artists']['items'][0]['uri']
    #print(uri)

    if function == 'getAlbums':
        artist = sys.argv[2]
        getArtistURI(spotify, artist)
    elif function == 'getTracks':
        album = sys.argv[2]
        #getAlbumURI(spotify, album)
    else:
        print('Usage: getAlbums ARTIST_NAME or getTracks ALBUM_NAME')


def getArtistURI(spotify, artist):
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

'''def getAlbumURI(spotify, album):
    album = ' ' + album

    results = spotify.search(q='album:' + album, type='album')
    uri = results['artists']['items'][0]['uri']

    results = spotify.artist_albums(uri, album_type='album')
    albums = results['items']
    while results['next']:
        results = spotify.next(results)
        albums.extend(results['items'])

    for album in albums:
        print(album['name'])'''

if __name__ == '__main__':
    main()