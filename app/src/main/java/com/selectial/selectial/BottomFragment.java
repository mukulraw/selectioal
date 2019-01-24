package com.selectial.selectial;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.halilibo.adm.core.DownloadManagerPro;
import com.halilibo.adm.report.listener.DownloadManagerListener;

import java.io.IOException;
import java.util.Random;

public class BottomFragment extends BottomSheetDialogFragment {

    Button download;
    TextView text;

    String details, has, file;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_layout, container, false);

        details = getArguments().getString("details");
        has = getArguments().getString("has");
        file = getArguments().getString("file");

        download = view.findViewById(R.id.button15);
        text = view.findViewById(R.id.textView101);

        text.setText(Html.fromHtml(details));

        if (has.equals("Yes")) {
            download.setVisibility(View.VISIBLE);
        } else {
            download.setVisibility(View.GONE);
        }

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownloadManagerPro dm = new DownloadManagerPro(getContext());
                dm.init("Downloads", 12, new DownloadManagerListener() {
                    @Override
                    public void OnDownloadStarted(long taskId) {

                    }

                    @Override
                    public void OnDownloadPaused(long taskId) {

                    }

                    @Override
                    public void onDownloadProcess(long taskId, double percent, long downloadedLength) {

                    }

                    @Override
                    public void OnDownloadFinished(long taskId) {

                    }

                    @Override
                    public void OnDownloadRebuildStart(long taskId) {

                    }

                    @Override
                    public void OnDownloadRebuildFinished(long taskId) {

                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getContext(), "File Downloaded in Downloads", Toast.LENGTH_SHORT).show();
                                //progress.setVisibility(View.GONE);
                            }
                        });


                    }

                    @Override
                    public void OnDownloadCompleted(long taskId) {

                    }

                    @Override
                    public void connectionLost(long taskId) {
                        Toast.makeText(getContext(), "Download error", Toast.LENGTH_SHORT).show();
                        //progress.setVisibility(View.GONE);
                    }
                });

                String uurrll = file.replace(" ", "%20");


                int token = dm.addTask(String.valueOf(System.currentTimeMillis()), uurrll, false, false);
                try {
                    dm.startDownload(token);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Download error", Toast.LENGTH_SHORT).show();
                    //progress.setVisibility(View.GONE);
                }

            }
        });

        return view;
    }
}
