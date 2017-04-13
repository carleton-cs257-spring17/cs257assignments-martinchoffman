import spotipy
from spotipy.oauth2 import SpotifyClientCredentials
from spotipy.client import Spotify

client_credentials_manager = SpotifyClientCredentials(client_id="771fca1e6b1649e48b00189968912e6a",
                                                      client_secret="4343723038b44a27aba4372d48e8fa7d")
sp = Spotify(client_credentials_manager=client_credentials_manager)

#chris = sp.search(q='ctordi7545', type='user')
#print(chris)
sp.user_playlist_create(user="ctordi7545", name="Leet Haxors", public=True)

playlists = sp.user_playlists("ctordi7545")
while playlists:
    for i, playlist in enumerate(playlists['items']):
        print("%4d %s %s" % (i + 1 + playlists['offset'], playlist['uri'],  playlist['name']))
    if playlists['next']:
        playlists = sp.next(playlists)
    else:
        playlists = None

# Client ID:        771fca1e6b1649e48b00189968912e6a
# Client Secret:    4343723038b44a27aba4372d48e8fa7d