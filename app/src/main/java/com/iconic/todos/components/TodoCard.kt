package com.iconic.todos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.iconic.todos.R
import com.iconic.todos.domain.models.Todo
import com.iconic.todos.utils.dateFormat

@Composable
fun TodoCard(
    update: (String) -> Unit,
    todo: Todo,
    index: Int,
    delete: (Todo) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
            .clickable { update(todo.id) }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = "$index.", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(.5f)
            ) {
                Text(
                    text = todo.title.ifEmpty { todo.description },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(text = dateFormat(todo.date)!!, style = MaterialTheme.typography.labelSmall)
            }

            Spacer(modifier = Modifier.width(15.dp))

            RoundIcon(
                color = MaterialTheme.colorScheme.error,
                icon = {
                    Icon(
                        tint = MaterialTheme.colorScheme.error,
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = ""
                    )
                },
                onClick = { delete(todo) }
            )
        }

    }
}