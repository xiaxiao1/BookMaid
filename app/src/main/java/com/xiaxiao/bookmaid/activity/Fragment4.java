package com.xiaxiao.bookmaid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.listener.OnFragmentResultListener;
import com.xiaxiao.bookmaid.util.BitmapUtil;
import com.xiaxiao.bookmaid.util.CropUtil;
import com.xiaxiao.bookmaid.util.GlideHelper;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;

import java.io.File;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment4 extends BaseFragment implements OnFragmentResultListener{
    UIDialog uiDialog;
    CircleImageView userHead_cimg;


    public Fragment4() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment4, container, false);
        initViews(view);
        uiDialog = new UIDialog(getActivity());

        GlideHelper.loadImage(getActivity(),Util.getUser().getHeadImage().getUrl(),userHead_cimg);

        return view;
    }


    public void initViews(View view) {
        userHead_cimg = (CircleImageView) view.findViewById(R.id.user_head);
        Button exit_btn = (Button) view.findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                getActivity().finish();
            }
        });



        userHead_cimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo();
            }
        });
    }

    public void photo() {
        Util.takePhoto(getActivity());
    }


    @Override
    public void OnFragmentResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BitmapUtil.PHOTO_PICKED_WITH_DATA:
                Util.toast(getActivity(),"从相册里选");
                Uri photo_uri = data.getData();
                try {
                    final Bitmap bitmap = Bitmap.createScaledBitmap(BitmapUtil.getThumbnail(photo_uri, getActivity()),
                            400, 400, true);

                    final File tempFile = CropUtil.makeTempFile(bitmap, "temp_file.jpg");
                    if (tempFile==null) {
                        return;
                    }
                    getBuilder().build()
                            .updateUserheadImage(tempFile, Util.getUser(), new BmobListener() {
                                @Override
                                public void onSuccess(Object object) {
                                    userHead_cimg.setImageBitmap(bitmap);
                                    tempFile.delete();
                                    Util.toast(getActivity(),"上传头像成功");
                                }

                                @Override
                                public void onError(BmobException e) {
                                    Util.toast(getActivity(),"上传头像失败");
                                    Util.L("上传头像失败"+e.getErrorCode()+e.getMessage());
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case BitmapUtil.CAMERA_WITH_DATA:
                Util.toast(getActivity(),"拍照的");
                final File file = new File(BitmapUtil.HEAD_IMAGE_PATH + "temp.jpg");
                try {
                    final Bitmap bitmap = Bitmap.createScaledBitmap(BitmapUtil.getThumbnail(file, getActivity()), 400,
                            400, true);
//                    userHead_cimg.setImageBitmap(bitmap);
                    final File tempFile = CropUtil.makeTempFile(bitmap, "temp_file.jpg");
                    getBuilder().build()
                            .updateUserheadImage(tempFile, Util.getUser(), new BmobListener() {
                                @Override
                                public void onSuccess(Object object) {
                                    userHead_cimg.setImageBitmap(bitmap);
                                    tempFile.delete();
                                    file.delete();
                                    Util.toast(getActivity(),"上传头像成功");
                                }

                                @Override
                                public void onError(BmobException e) {
                                    Util.toast(getActivity(),"上传头像失败");
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
