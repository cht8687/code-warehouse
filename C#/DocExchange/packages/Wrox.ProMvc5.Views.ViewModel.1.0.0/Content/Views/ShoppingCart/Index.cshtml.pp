@model $rootnamespace$.Models.ShoppingCartViewModel

@{
    ViewBag.Title = "Shopping Cart";
}

<h2>@Model.Message</h2>
<fieldset>
    <legend>Shopping Cart</legend>

    <table>
        <caption>Products in Cart</caption>
        <thead>
        <tr>
            <th>Product</th><th>Price</th>
        </tr>
        </thead>
        <tbody>
        @foreach (var product in Model.Products) {
            <tr>
                <td>@product.Title</td><td>@product.Price</td>
            </tr>
        }
        </tbody>
        <tfoot>
            <tr>
                <td><strong>Total</strong></td><td>@Model.CartTotal</td>
            </tr>
        </tfoot>
    </table>

</fieldset>
