package com.johncodeos.asktorate

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.stkent.amplify.prompt.BasePromptViewConfig
import com.github.stkent.amplify.prompt.DefaultLayoutPromptView
import com.github.stkent.amplify.prompt.DefaultLayoutPromptViewConfig
import com.github.stkent.amplify.tracking.Amplify
import kotlinx.android.synthetic.main.review_row.view.*
import kotlinx.android.synthetic.main.post_row.view.*

class Social_RVAdapter(private val socialCells: List<SocialModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    class RateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private lateinit var mContext: Context
    private var RATE_VIEW = 0
    private var POST_VIEW = 1

    private var RATE_VIEW_POSITION = 1

    lateinit var promptView: DefaultLayoutPromptView
    lateinit var basePromptViewConfig: BasePromptViewConfig
    lateinit var defaultLayoutPromptViewConfig: DefaultLayoutPromptViewConfig


    override fun getItemViewType(position: Int): Int {
        return if (position == RATE_VIEW_POSITION) {
            RATE_VIEW
        }else{
            POST_VIEW
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        if (viewType == RATE_VIEW) {
            val rateView = LayoutInflater.from(parent.context).inflate(R.layout.review_row, parent, false)
            val rateViewHolder = RateViewHolder(rateView)
            return rateViewHolder
        }else if (viewType == POST_VIEW) {
            val postView = LayoutInflater.from(parent.context).inflate(R.layout.post_row, parent, false)
            val postViewHolder = PostViewHolder(postView)
            return postViewHolder
        }
        return null!!
    }




    override fun getItemCount(): Int {
        //We adding 1 more row in the size of the array for our Rate View
        return socialCells.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == RATE_VIEW) {
            val rateViewHolder = holder as RateViewHolder
            promptView = rateViewHolder.itemView.prompt_view
            setupPromptViewConfig()
            setupPromptViewColors(R.color.promptBackgroundColor, R.color.promptTextColor)
            promptView.applyBaseConfig(basePromptViewConfig)
            promptView.applyConfig(defaultLayoutPromptViewConfig)
            Amplify.getSharedInstance().promptIfReady(promptView)
        }else if (holder.itemViewType == POST_VIEW) {
            val currentPosition: Int
            val postViewHolder = holder as PostViewHolder
            if (position > RATE_VIEW_POSITION) {
                currentPosition = position - 1
            }else {
                currentPosition = position
            }
            postViewHolder.itemView.userProfilePic_imageview.setImageResource(socialCells[currentPosition].userProfilePic)
            postViewHolder.itemView.userName_label.text = socialCells[currentPosition].userName
            postViewHolder.itemView.userDate_label.text = socialCells[currentPosition].userDate
            postViewHolder.itemView.userDesc_textview.text = socialCells[currentPosition].userDesc
            postViewHolder.itemView.userPhoto_imageview.setImageResource(socialCells[currentPosition].userPhoto)
        }
    }

    private fun setupPromptViewConfig() {
        basePromptViewConfig = BasePromptViewConfig.Builder()
            .setUserOpinionQuestionTitle("Do you like Social App?")
            .setUserOpinionQuestionPositiveButtonLabel("Yeah!")
            .setUserOpinionQuestionNegativeButtonLabel("Nah")
            .setPositiveFeedbackQuestionTitle("Awesome! Would you like to rate it?")
            .setPositiveFeedbackQuestionSubtitle("It helps a lot :)")
            .setPositiveFeedbackQuestionPositiveButtonLabel("Sure!")
            .setPositiveFeedbackQuestionNegativeButtonLabel("Not right now")
            .setCriticalFeedbackQuestionTitle(":( Would you like to send feedback?")
            .setCriticalFeedbackQuestionPositiveButtonLabel("Sure!")
            .setCriticalFeedbackQuestionNegativeButtonLabel("Not right now")
            .setThanksTitle("Thank you!!!")
            .setThanksDisplayTimeMs(2000)
            .build()
    }

    private fun setupPromptViewColors(backgroundColor: Int, textcolor: Int) {
        defaultLayoutPromptViewConfig = DefaultLayoutPromptViewConfig.Builder()
            .setForegroundColor(ContextCompat.getColor(mContext, textcolor))
            .setBackgroundColor(ContextCompat.getColor(mContext, backgroundColor))
            .setTitleTextColor(ContextCompat.getColor(mContext, textcolor))
            .setSubtitleTextColor(ContextCompat.getColor(mContext, textcolor))
            .setPositiveButtonTextColor(ContextCompat.getColor(mContext, textcolor))
            .setPositiveButtonBackgroundColor(ContextCompat.getColor(mContext, backgroundColor))
            .setPositiveButtonBorderColor(ContextCompat.getColor(mContext, textcolor))
            .setNegativeButtonTextColor(ContextCompat.getColor(mContext, textcolor))
            .setNegativeButtonBackgroundColor(ContextCompat.getColor(mContext, backgroundColor))
            .setNegativeButtonBorderColor(ContextCompat.getColor(mContext, textcolor))
            .build()
    }

}