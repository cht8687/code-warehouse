@using $rootnamespace$.Models;

<ul>
@foreach(Album p in (ViewBag.Albums as IEnumerable<Album>)) {
  <li>@p.Title</li>
}
</ul>

<ul>
@foreach (dynamic p in ViewBag.Albums) {
  <li>@p.Title</li>
}
</ul>
