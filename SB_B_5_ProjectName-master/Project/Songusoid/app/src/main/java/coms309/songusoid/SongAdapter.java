package coms309.songusoid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Peter Marasco on 11/30/2017.
 * It is an adapter that Handles the Songs in makes them displayable
 */

public class SongAdapter extends BaseAdapter {

    private final Context mContext;
    private final Song[] songs;

    /**
     * Constructor for the adapter
     * @param context
     * @param Songs The list of songs
     */
    public SongAdapter(Context context, Song[] Songs) {
        this.mContext = context;
        this.songs = Songs;
    }


    @Override
    public int getCount() {
        return songs.length;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Creates the view for the Specific song
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Song song = songs[position];


        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.linearlayout_song, null);
        }

        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageview_cover_art);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.textview_book_name);
        final TextView authorTextView = (TextView)convertView.findViewById(R.id.textview_book_author);
        final TextView numUpVotes= (TextView)convertView.findViewById(R.id.NumUpVotes);
        nameTextView.setText(songs[position].getSongName());
        nameTextView.setTextColor(Color.BLACK);
        authorTextView.setText(songs[position].getComposer());
        authorTextView.setTextColor(Color.BLACK);
        numUpVotes.setText(""+songs[position].getUpvotes());
        numUpVotes.setTextColor(Color.BLACK);
        return convertView;
    }
}