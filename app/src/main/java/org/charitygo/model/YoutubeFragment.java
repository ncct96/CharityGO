package org.charitygo.model;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.charitygo.R;

public class YoutubeFragment extends Fragment {

    public YoutubeFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View listView = getActivity().findViewById(R.id.youTubeVid);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        YoutubeFragment fragment = (YoutubeFragment) getFragmentManager().findFragmentById(R.id.youTubeVid);
        fragmentTransaction.replace(R.id.youTubeVid, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
