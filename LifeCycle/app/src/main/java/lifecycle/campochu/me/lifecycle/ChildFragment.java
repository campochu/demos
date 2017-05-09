package lifecycle.campochu.me.lifecycle;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ckb on 17/5/8.
 */

public class ChildFragment extends Fragment {

    private static final String LOG_TAG = "A--F|";

    @Override
    public void onAttach(Context context) {
        Log.i(LOG_TAG, "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_child, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onViewCreated");

        view.findViewById(R.id.textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EmptyActivity.class));
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i(LOG_TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(LOG_TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(LOG_TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onInflate");
        super.onInflate(context, attrs, savedInstanceState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onViewStateRestored");
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onPause() {
        Log.i(LOG_TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(LOG_TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i(LOG_TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i(LOG_TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i(LOG_TAG, "onDetach");
        super.onDetach();
    }
}
