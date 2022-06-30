package com.johncodeos.asktorate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.stkent.amplify.prompt.BasePromptViewConfig
import com.github.stkent.amplify.prompt.DefaultLayoutPromptView
import com.github.stkent.amplify.prompt.DefaultLayoutPromptViewConfig
import com.github.stkent.amplify.tracking.Amplify

class SocialRVAdapter(private val socialCells: List<SocialModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private lateinit var mContext: Context

    companion object {
        const val RATE_VIEW = 0
        const val POST_VIEW = 1
        const val RATE_VIEW_POSITION = 1
    }

    private lateinit var basePromptViewConfig: BasePromptViewConfig
    private lateinit var defaultLayoutPromptViewConfig: DefaultLayoutPromptViewConfig


    override fun getItemViewType(position: Int): Int {
        return if (position == RATE_VIEW_POSITION) {
            RATE_VIEW
        } else {
            POST_VIEW
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        if (viewType == RATE_VIEW) {
            val rateView =
                LayoutInflater.from(parent.context).inflate(R.layout.review_row, parent, false)
            val rateViewHolder = RateViewHolder(rateView)
            return rateViewHolder
        } else if (viewType == POST_VIEW) {
            val postView =
                LayoutInflater.from(parent.context).inflate(R.layout.post_row, parent, false)
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

            val promptView =
                rateViewHolder.itemView.findViewById<DefaultLayoutPromptView>(R.id.prompt_view)
            setupPromptViewConfig()
            setupPromptViewColors(R.color.promptBackgroundColor, R.color.promptTextColor)
            promptView.applyBaseConfig(basePromptViewConfig)
            promptView.applyConfig(defaultLayoutPromptViewConfig)
            Amplify.getSharedInstance().promptIfReady(promptView)
        } else if (holder.itemViewType == POST_VIEW) {
            val postViewHolder = holder as PostViewHolder
            val currentPosition: Int = if (position > RATE_VIEW_POSITION) {
                position - 1
            } else {
                position
            }

            val userProfilePicImageView =
                postViewHolder.itemView.findViewById<ImageView>(R.id.user_profile_pic_imageview)
            userProfilePicImageView.setImageResource(socialCells[currentPosition].userProfilePic)

            val userNameTextView =
                postViewHolder.itemView.findViewById<TextView>(R.id.username_textview)
            userNameTextView.text = socialCells[currentPosition].userName

            val userDateTextView =
                postViewHolder.itemView.findViewById<TextView>(R.id.user_date_textview)
            userDateTextView.text = socialCells[currentPosition].userDate

            val userDescTextview =
                postViewHolder.itemView.findViewById<TextView>(R.id.user_desc_textview)
            userDescTextview.text = socialCells[currentPosition].userDesc

            val userPhotoImageview =
                postViewHolder.itemView.findViewById<ImageView>(R.id.user_photo_imageview)
            userPhotoImageview.setImageResource(socialCells[currentPosition].userPhoto)
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

    private fun setupPromptViewColors(backgroundColor: Int, textColor: Int) {
        defaultLayoutPromptViewConfig = DefaultLayoutPromptViewConfig.Builder()
            .setForegroundColor(ContextCompat.getColor(mContext, textColor))
            .setBackgroundColor(ContextCompat.getColor(mContext, backgroundColor))
            .setTitleTextColor(ContextCompat.getColor(mContext, textColor))
            .setSubtitleTextColor(ContextCompat.getColor(mContext, textColor))
            .setPositiveButtonTextColor(ContextCompat.getColor(mContext, textColor))
            .setPositiveButtonBackgroundColor(ContextCompat.getColor(mContext, backgroundColor))
            .setPositiveButtonBorderColor(ContextCompat.getColor(mContext, textColor))
            .setNegativeButtonTextColor(ContextCompat.getColor(mContext, textColor))
            .setNegativeButtonBackgroundColor(ContextCompat.getColor(mContext, backgroundColor))
            .setNegativeButtonBorderColor(ContextCompat.getColor(mContext, textColor))
            .build()
    }

}