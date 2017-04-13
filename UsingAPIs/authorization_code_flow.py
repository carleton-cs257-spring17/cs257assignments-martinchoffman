import sys

import spotipy
import spotipy.util as util
import spotipy.oauth2 as oauth2

auth = oauth2.SpotifyOAuth(client_id='771fca1e6b1649e48b00189968912e6a',
                           client_secret='4343723038b44a27aba4372d48e8fa7d',
                           redirect_uri='https://www.google.com/')

print(auth.get_authorize_url())

'''scope = 'user-library-read'

if len(sys.argv) > 1:
    username = sys.argv[1]
else:
    print("Usage: %s username" % (sys.argv[0],))
    sys.exit()

token = util.prompt_for_user_token(username, scope)

if token:
    sp = spotipy.Spotify(auth=token)
    results = sp.current_user_saved_tracks()
    for item in results['items']:
        track = item['track']
        print(track['name'] + ' - ' + track['artists'][0]['name'])
else:
    print("Can't get token for", username)'''
