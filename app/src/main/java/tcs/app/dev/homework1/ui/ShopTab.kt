package tcs.app.dev.homework1.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tcs.app.dev.homework1.data.Cart
import tcs.app.dev.homework1.data.Euro
import tcs.app.dev.homework1.data.Item
import tcs.app.dev.homework1.data.MockData
import tcs.app.dev.homework1.data.Shop
import tcs.app.dev.R

@Composable
fun ShopTab(
    shop: Shop,
    cart: Cart,
    modifier: Modifier = Modifier,
    onAddToCart: (Item) -> Unit
) {
    val priceEntries = shop.prices.toList()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(9.dp),
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        items(
            items = priceEntries,
            key = { (item, _) -> item.id }
        ) { (item, price) ->
            val amountInCart = cart.items[item] ?: 0u
            ShopItemRow(
                item = item,
                price = price,
                amountInCart = amountInCart,
                onAddToCart = { onAddToCart(item) }
            )
        }
    }
}

@Composable
private fun ShopItemRow(
    item: Item,
    price: Euro,
    amountInCart: UInt,
    onAddToCart: () -> Unit
) {
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
            Text(text = price.toString())
        }
        Column(horizontalAlignment = Alignment.End) {
            if (amountInCart > 0u) {
                Text(text = "x$amountInCart")
                Spacer(modifier = Modifier.height(4.dp))
            }
            Button(onClick = onAddToCart) {
                Text(stringResource(R.string.label_add))
            }
        }
    }
}