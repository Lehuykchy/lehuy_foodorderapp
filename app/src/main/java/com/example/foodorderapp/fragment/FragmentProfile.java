package com.example.foodorderapp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.R;
import com.example.foodorderapp.activity.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class FragmentProfile extends Fragment {
    private NavController navController;
    private View mView;
    private TextView tvProfileName,tvprofileProfileInfo,
            tvProfileCenterSp, tvProfileChangePassword, tvProfileRegulaytoryPolicy, tvProfileLocation, tvRestaurant;
    private ImageView imgProfile, imgEditname;
    private Button btLogout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        initUI();
        showUserProfile();


        imgEditname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_framentProfile_to_fragmentProfileEditname);

            }
        });

        onClickTvprofileProfileInfo();
        onClickTvProfileLocation();
        onClickTvProfileCenterSp();
        onclickTvProfileChangePassword();
        onClickTvProfileRegulaytoryPolicy();
        onClickTvProfileRestaurant();
        onClickBtLogout();


        return mView;
    }

    private void onClickTvProfileRestaurant() {
        tvRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_framentProfile_to_fragmentProfileRestaurantsEXXXX);
            }
        });

    }

    private void onClickTvProfileLocation() {
        tvProfileLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_framentProfile_to_fragmentProfileLocation);
            }
        });
    }

    private void onClickBtLogout() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void onClickTvProfileRegulaytoryPolicy() {
        tvProfileRegulaytoryPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_framentProfile_to_fragmentProfileRegulatoryPolicy);
            }
        });
    }

    private void onclickTvProfileChangePassword() {
        tvProfileChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_framentProfile_to_fragmentProfileChangePassword);
            }
        });
        
    }

    private void onClickTvProfileCenterSp() {
        tvProfileCenterSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_framentProfile_to_fragmentProfileCenterSupport);
            }
        });
    }

    private void onClickTvprofileProfileInfo() {
        tvprofileProfileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_framentProfile_to_fragmentProfileInfomation);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private void showUserProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        tvProfileName.setText(name);
        Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.imgprofile).into(imgProfile);
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        imgEditname.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentProfileEditname fragmentProfileEditname = new FragmentProfileEditname();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_container, fragmentProfileEditname);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });
//
//        clickProfileInfo();
//    }

//    private void clickProfileInfo() {
//        tvprofileProfileInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentProfileInfomation fragmentProfileInfomation = new FragmentProfileInfomation();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_container, fragmentProfileInfomation);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });
//    }

    private void initUI() {
        tvProfileName = mView.findViewById(R.id.tvprofile_name);
        imgProfile = mView.findViewById(R.id.imgprofile);
        imgEditname = mView.findViewById(R.id.imgv_editname);
        tvprofileProfileInfo = mView.findViewById(R.id.tvprofile_profileinfo);
        tvProfileCenterSp = mView.findViewById(R.id.tvprofile_support);
        tvProfileRegulaytoryPolicy = mView.findViewById(R.id.tvprofile_policy);
        tvProfileChangePassword = mView.findViewById(R.id.tvprofile_password);
        btLogout = mView.findViewById(R.id.btprofile_logout);
        tvProfileLocation = mView.findViewById(R.id.tvprofile_location);
        tvRestaurant = mView.findViewById(R.id.tvprofile_addfood);
    }


}
