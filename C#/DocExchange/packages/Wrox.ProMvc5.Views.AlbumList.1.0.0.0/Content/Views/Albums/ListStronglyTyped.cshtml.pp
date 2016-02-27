@using $rootnamespace$.Models;
@model IEnumerable<Album>

<ul>
@foreach(Album p in Model) {
  <li>@p.Title</li>
}
</ul>
