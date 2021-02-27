package com.example.androiddevchallenge

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.DataProvider
import com.example.androiddevchallenge.data.model.Puppy
import com.example.androiddevchallenge.ui.theme.purple500

@Composable
fun ProfileScreen(puppy: Puppy, onNavIconPressed: () -> Unit = { }) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(modifier = Modifier.weight(1f)) {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {
                    ProfileHeader(
                        scrollState,
                        puppy,
                        this@BoxWithConstraints.maxHeight
                    )
                    ProfileContent(puppy, this@BoxWithConstraints.maxHeight)
                }
            }
            AdoptFab(
                extended = scrollState.value == 0,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
private fun ProfileHeader(
    scrollState: ScrollState,
    puppy: Puppy,
    containerHeight: Dp
) {
    val offset = (scrollState.value / 2)
    val offsetDp = with(LocalDensity.current) { offset.toDp() }

    Image(
        modifier = Modifier
            .heightIn(max = containerHeight / 2)
            .fillMaxWidth()
            .padding(top = offsetDp),
        painter = painterResource(id = puppy.puppyImageId),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}

@Composable
private fun ProfileContent(puppy: Puppy, containerHeight: Dp) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))

        Name(puppy)

        ProfileProperty(stringResource(R.string.sex), puppy.sex)

        ProfileProperty(stringResource(R.string.age), puppy.age.toString())

        ProfileProperty(stringResource(R.string.personality), puppy.description)

        // Add a spacer that always shows part (320.dp) of the fields list regardless of the device,
        // in order to always leave some content at the top.
        Spacer(Modifier.height((containerHeight - 320.dp).coerceAtLeast(0.dp)))
    }
}

@Composable
private fun Name(
    puppy: Puppy
) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Name(
            puppy = puppy,
            modifier = Modifier.baselineHeight(32.dp)
        )
    }
}

@Composable
private fun Name(puppy: Puppy, modifier: Modifier = Modifier) {
    Text(
        text = puppy.title,
        modifier = modifier,
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ProfileProperty(label: String, value: String, isLink: Boolean = false) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Divider()
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = label,
                modifier = Modifier.baselineHeight(24.dp),
                style = MaterialTheme.typography.caption,
            )
        }
        val style = if (isLink) {
            MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary)
        } else {
            MaterialTheme.typography.body1
        }
        Text(
            text = value,
            modifier = Modifier.baselineHeight(24.dp),
            style = style
        )
    }
}

@Composable
fun AdoptFab(extended: Boolean, modifier: Modifier = Modifier) {
        FloatingActionButton(
            onClick = { /* TODO */ },
            modifier = modifier
                .padding(16.dp)
                .padding()
                .height(48.dp)
                .widthIn(min = 48.dp),
            backgroundColor = purple500,
            contentColor = Color.White
        ) {
            AnimatingFabContent(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Call,
                        contentDescription = stringResource(R.string.adopt_me)
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.adopt_me),
                    )
                },
                extended = extended

            )
        }
}

@Preview
@Composable
fun ProfilePreview() {
    val puppy = DataProvider.puppy
    ProfileScreen(puppy = puppy)
}