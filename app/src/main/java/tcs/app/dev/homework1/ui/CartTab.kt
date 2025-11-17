package tcs.app.dev.homework1.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tcs.app.dev.homework1.data.Cart
import tcs.app.dev.homework1.data.Discount
import tcs.app.dev.homework1.data.Euro
import tcs.app.dev.homework1.data.Item
import tcs.app.dev.homework1.data.MockData
import tcs.app.dev.homework1.data.times
import tcs.app.dev.R

@Composable
fun CartTab(
    cart: Cart,
    modifier: Modifier = Modifier,
    onUpdateItemAmount: (Item, UInt) -> Unit,
    onRemoveDiscount: (Discount) -> Unit,
    onPay: () -> Unit
) {
    if (cart.itemCount == 0u) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(stringResource(R.string.cart_empty))
        }
        return
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(9.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            items(
                items = cart.items.toList(),
                key = { (item, _) -> item.id }
            ) { (item, amount) ->
                CartItemRow(
                    item = item,
                    amount = amount,
                    pricePerUnit = cart.shop.prices[item]!!,
                    onIncrement = {
                        val newAmount = amount + 1u
                        onUpdateItemAmount(item, newAmount)
                    },
                    onDecrement = {
                        val newAmount = if (amount == 0u) 0u else amount - 1u
                        onUpdateItemAmount(item, newAmount)
                    }
                )
            }
            items(
                items = cart.discounts,
                key = { it.hashCode() }
            ) { discount ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = discountDescription(discount),
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = { onRemoveDiscount(discount) }) {
                        Text(stringResource(R.string.label_remove))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(9.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.total_price, cart.price),
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = onPay,
                enabled = cart.itemCount > 0u
            ) {
                Text(stringResource(R.string.label_pay))
            }
        }
    }
}

@Composable
private fun CartItemRow(
    item: Item,
    amount: UInt,
    pricePerUnit: Euro,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    val total = pricePerUnit * amount

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(MockData.getImage(item)),
            contentDescription = null,
            modifier = Modifier.size(56.dp)
        )
        Spacer(modifier = Modifier.width(9.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = stringResource(MockData.getName(item)))
            Text(text = "Unit: $pricePerUnit Total: $total")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDecrement) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Decrease"
                )
            }
            Text(text = amount.toString())
            IconButton(onClick = onIncrement) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase"
                )
            }
        }
    }
}