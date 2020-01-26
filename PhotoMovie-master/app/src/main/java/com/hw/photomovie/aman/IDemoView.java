package com.hw.photomovie.aman;

import android.app.Activity;
import com.hw.photomovie.render.GLTextureView;
import com.hw.photomovie.aman.widget.FilterItem;
import com.hw.photomovie.aman.widget.TransferItem;

import java.util.List;

/**
 * Created by huangwei on 2018/9/9.
 */
public interface IDemoView {
    public GLTextureView getGLView();
    void setFilters(List<FilterItem> filters);
    Activity getActivity();

    void setTransfers(List<TransferItem> items);
}
