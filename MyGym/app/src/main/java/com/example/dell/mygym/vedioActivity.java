package com.example.dell.mygym;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class vedioActivity extends Activity {
    private ListView mListView;
    private List<VideoInfo> videoList=new ArrayList<VideoInfo>();
    private VideoInfo video1;
    private VideoInfo video2;
    private VideoInfo video3;
    private myAdapter adapter;
    private int currentIndex=-1;
    private String url1="http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private String url2="http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private String url3="http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private VideoView mVideoView;
    MediaController mMediaCtrl;
    private int playPosition=-1;
    private boolean isPaused=false;
    private boolean isPlaying=false;
    private Button bc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        //构造视频数据
        video1=new VideoInfo(url1,"COURSEVEDIO2","It's about yoga!",R.drawable.vedio2);
        video2=new VideoInfo(url2,"COURSEVEDIO2","The course is teached by Liu!",R.drawable.vedio3);
        video3=new VideoInfo(url3,"COURSEVEDIO3","the course is very hot!",R.drawable.vedio2);
        videoList.add(video1);
        videoList.add(video2);
        videoList.add(video3);
        mListView=(ListView) findViewById(R.id.video_list);
        bc=(Button)findViewById((R.id.vbh));
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Intent intent = new Intent(vedioActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        adapter = new myAdapter();
        mListView.setAdapter(adapter);
        mListView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if((currentIndex<firstVisibleItem || currentIndex>mListView.getLastVisiblePosition())&&isPlaying){
                    System.out.println("滑动的："+mVideoView.toString());
                    playPosition=mVideoView.getCurrentPosition();
                    mVideoView.pause();
                    mVideoView.setMediaController(null);
                    isPaused=true;
                    isPlaying=false;
                    System.out.println("视频已经暂停："+playPosition);
                }
            }
        });
/*		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				currentIndex=position;
				adapter.notifyDataSetChanged();
			}
		});*/
    }

    class myAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            // TODO Auto-generated method stubs
            return videoList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return videoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            final int mPosition=position;
            if(convertView==null){
                Log.d("1",convertView+"convertView==null");
                convertView=LayoutInflater.from(vedioActivity.this).inflate(R.layout.video_item, null);
                holder=new ViewHolder();
                holder.videoImage=(ImageView) convertView.findViewById(R.id.video_image);
                holder.videoNameText=(TextView)convertView.findViewById(R.id.video_name_text);
                holder.videoJj=(TextView)convertView.findViewById(R.id.video_info);
                holder.videoPlayBtn=(ImageButton)convertView.findViewById(R.id.video_play_btn);
                holder.mProgressBar=(ProgressBar) convertView.findViewById(R.id.progressbar);
                convertView.setTag(holder);
            }else{
                Log.d("2",convertView+"convertView!=null");
                holder=(ViewHolder) convertView.getTag();
            }
            holder.videoImage.setImageDrawable(getResources().getDrawable(videoList.get(position).getVideoImage()));
            holder.videoNameText.setText(videoList.get(position).getVideoName());
            holder.videoJj.setText(videoList.get(position).getVideoJj());
            holder.videoPlayBtn.setVisibility(View.VISIBLE);
            holder.videoImage.setVisibility(View.VISIBLE);
            holder.videoNameText.setVisibility(View.VISIBLE);
            holder.videoJj.setVisibility(View.VISIBLE);
            mMediaCtrl = new MediaController(vedioActivity.this,false);
            if(currentIndex == position){
                Log.d("3",currentIndex+"currentIndex == position");
                holder.videoPlayBtn.setVisibility(View.INVISIBLE);
                holder.videoImage.setVisibility(View.INVISIBLE);
                holder.videoNameText.setVisibility(View.INVISIBLE);
                holder.videoJj.setVisibility(View.INVISIBLE);
                if(isPlaying || playPosition==-1){
                    if(mVideoView!=null){
                        Log.d("4",mVideoView+"mVideoView!=null");

                        mVideoView.setVisibility(View.GONE);
                        mVideoView.stopPlayback();
                        holder.mProgressBar.setVisibility(View.GONE);
                    }
                }
                mVideoView=(VideoView) convertView.findViewById(R.id.videoview);
                mVideoView.setVisibility(View.VISIBLE);
                mMediaCtrl.setAnchorView(mVideoView);
                mMediaCtrl.setMediaPlayer(mVideoView);
                mVideoView.setMediaController(mMediaCtrl);
                mVideoView.requestFocus();
                holder.mProgressBar.setVisibility(View.VISIBLE);
                if(playPosition>0 && isPaused){
                    mVideoView.start();
                    isPaused=false;
                    isPlaying=true;
                    holder.mProgressBar.setVisibility(View.GONE);
                }else{
                    mVideoView.setVideoPath(videoList.get(mPosition).getUrl());
                    isPaused=false;
                    isPlaying=true;
                    System.out.println("播放新的视频");
                }
                mVideoView.setOnCompletionListener(new OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(mVideoView!=null){
                            mVideoView.seekTo(0);
                            mVideoView.stopPlayback();
                            currentIndex=-1;
                            isPaused=false;
                            isPlaying=false;
                            holder.mProgressBar.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                mVideoView.setOnPreparedListener(new OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        holder.mProgressBar.setVisibility(View.GONE);
                        mVideoView.start();
                    }
                });

            }else{
                holder.videoPlayBtn.setVisibility(View.VISIBLE);
                holder.videoImage.setVisibility(View.VISIBLE);
                holder.videoNameText.setVisibility(View.VISIBLE);
                holder.videoJj.setVisibility(View.VISIBLE);
                holder.mProgressBar.setVisibility(View.GONE);
            }

            holder.videoPlayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentIndex=mPosition;
                    playPosition=-1;
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        };
    }
    static class ViewHolder{
        ImageView videoImage;
        TextView videoNameText;
        TextView videoJj;
        ImageButton videoPlayBtn;
        ProgressBar mProgressBar;

    }
    static class VideoInfo {
        private String url;
        private String videoName;
        private String videoJj;
        private int videoImage;
        public VideoInfo(String url,String name,String videoJj,int path) {
            this.videoName=name;
            this.videoImage=path;
            this.videoJj=videoJj;
            this.url=url;
        }
        public String getVideoName() {
            return videoName;
        }
        public String getVideoJj() {
            return videoJj;
        }
        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }
        public int getVideoImage() {
            return videoImage;
        }
        public void setVideoImage(int videoImage) {
            this.videoImage = videoImage;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
    }
}
