package douban.subview

import android.content.Context
import douban.DouBanV1
import douban.FilmDetail
import douban.adapter.FilmPhotoAdapter
import michaelzhao.R
import util.Rx
import kotlin.math.abs
import kotlin.math.max

//电影剧照
class FilmViewPhoto(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmDetail = filmDetail

    init {
        initRecyclerView(3)
        initSwipeLayout()
        initSwitchBtn()
        initPageSwitch()
    }

    override fun onNormalClick() {
        ShowPageSwitch(false)
        mRecyclerView.adapter = FilmPhotoAdapter(mContext, mFilmDetail)
    }

    override fun onMoreClick() {
        ShowPageSwitch()
        onSwitchPage(currPage)
    }

    override fun onSwitchPage(page: Int) {
        EnablePageSwitch(false)
        ShowSwipe()
        Rx.get {
            DouBanV1.getFilmPhoto(mFilmDetail.id, abs(page - 1) * 30)
        }.set {
            setTotalPage(max(it.total / 30 + 1, 1))
            mRecyclerView.adapter = FilmPhotoAdapter(mRecyclerView, it)
        }.end {
            EnablePageSwitch()
            ShowSwipe(false)
        }
    }


}