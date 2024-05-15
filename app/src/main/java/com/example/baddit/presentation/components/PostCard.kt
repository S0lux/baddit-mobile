package com.example.baddit.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.baddit.R
import com.example.baddit.data.model.posts.Author
import com.example.baddit.data.model.posts.Community
import com.example.baddit.data.model.posts.PostDTOItem
import com.example.baddit.ui.theme.BadditTheme
import com.example.baddit.ui.theme.CustomTheme.cardBackground
import com.example.baddit.ui.theme.CustomTheme.cardForeground
import com.example.baddit.ui.theme.CustomTheme.cardTitleText
import getTimeAgoFromUtcString
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCard(postDetails: PostDTOItem) {
    var voteInteractionSource = remember { MutableInteractionSource() }
    var voteBoxInteractionColor by remember { mutableStateOf(Color(0xFFFFA500)) }
    var coroutineScope = rememberCoroutineScope();

    var votePosition by remember { mutableStateOf(IntOffset.Zero) }
    var voteElementSize by remember { mutableStateOf(IntSize.Zero) }


    Box(
        Modifier
            .background(MaterialTheme.colorScheme.cardBackground)
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(postDetails.community.logoUrl).build(),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(32.dp)
                        .aspectRatio(1f),
                )

                Column {
                    Row {
                        Text(
                            "r/",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF9E9E9E),
                            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                        )

                        Text(
                            postDetails.community.name,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF2196F3),
                            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text(
                            postDetails.author.username,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF9E9E9E),
                            modifier = Modifier.padding(0.dp),
                            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                        )

                        Text(
                            "•",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF9E9E9E),
                            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                        )

                        Text(
                            getTimeAgoFromUtcString(postDetails.createdAt),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF9E9E9E),
                            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                        )
                    }
                }

            }

            Text(
                postDetails.title,
                color = MaterialTheme.colorScheme.cardTitleText,
                fontSize = 15.sp,
                lineHeight = 20.sp
            )

            if (postDetails.type == "TEXT") {
                Text(
                    postDetails.content,
                    color = MaterialTheme.colorScheme.cardTitleText,
                    fontSize = 10.sp,
                    lineHeight = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10))
                        .background(MaterialTheme.colorScheme.cardForeground)
                        .padding(5.dp),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            votePosition = coordinates.positionInWindow().round()
                            voteElementSize = coordinates.size
                        }
                        .clip(RoundedCornerShape(10))
                        .clickable(
                            onClick = {},
                            interactionSource = voteInteractionSource,
                            indication = rememberRipple(
                                bounded = true,
                                color = voteBoxInteractionColor
                            )
                        )
                        .background(MaterialTheme.colorScheme.cardForeground)
                        .padding(2.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_upvote),
                        contentDescription = null,
                        tint = if (postDetails.voteState == "UPVOTE") Color(0xFFFFA500) else Color.Black,
                        modifier = Modifier.clickable(
                            onClick = {
                                coroutineScope.launch {
                                    val pressPosition = Offset(
                                        x = voteElementSize.width / 6f,
                                        y = voteElementSize.height / 2f
                                    )
                                    val press = PressInteraction.Press(pressPosition);
                                    voteBoxInteractionColor = Color(0xFFFFA500)
                                    voteInteractionSource.emit(press)
                                    delay(300)
                                    voteInteractionSource.emit(PressInteraction.Release(press))
                                }
                            }
                        )
                    )

                    Text(
                        postDetails.score.toString(),
                        color = MaterialTheme.colorScheme.cardTitleText,
                        fontSize = 12.sp,
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.arrow_upvote),
                        contentDescription = null,
                        tint = if (postDetails.voteState == "DOWNVOTE") Color.Blue else Color.Black,
                        modifier = Modifier.rotate(180f).clickable(
                            onClick = {
                                coroutineScope.launch {
                                    val pressPosition = Offset(
                                        x = voteElementSize.width / 1f,
                                        y = voteElementSize.height / 2f
                                    )
                                    val press = PressInteraction.Press(pressPosition);
                                    voteBoxInteractionColor = Color.Blue
                                    voteInteractionSource.emit(press)
                                    delay(300)
                                    voteInteractionSource.emit(PressInteraction.Release(press))
                                }
                            }
                        )
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(10))
                        .background(MaterialTheme.colorScheme.cardForeground)
                        .padding(bottom = 2.dp, top = 2.dp, start = 6.dp, end = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.comment),
                        contentDescription = null,
                        tint = if (postDetails.voteState == "UPVOTE") Color(0xFFFFA500) else Color.Black,
                        modifier = Modifier.size(18.dp)
                    )

                    Text(
                        postDetails.commentCount.toString(),
                        color = MaterialTheme.colorScheme.cardTitleText,
                        fontSize = 12.sp,
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostCardPreview() {
    val details: PostDTOItem = PostDTOItem(
        id = "992e0a44-6682-4d13-b75e-834494679b65",
        type = "TEXT",
        title = "How the hell do I use this app? The mobile design absolutely sucks!!",
        content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        score = 0,
        voteState = Unit,
        commentCount = 0,
        author = Author(
            id = "b68eccfa-aa50-44fb-bffd-68fbd719d561",
            username = "trungkhang1",
            avatarUrl = "https://placehold.co/400.png"
        ),
        community = Community(
            name = "pesocommunity",
            logoUrl = "https://placehold.co/400.png"
        ),
        createdAt = "2024-05-13T05:57:03.877Z",
        updatedAt = "2024-05-13T05:57:03.877Z"
    )
    BadditTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(contentAlignment = Alignment.Center) {
                PostCard(details)
            }
        }
    }
}