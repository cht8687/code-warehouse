using System.Web.Mvc;

namespace $rootnamespace$.Controllers {
    public class SampleController : Controller {
        public ActionResult Index() {
            ViewBag.Message = "Welcome to ASP.NET MVC!";
            return View();
        }

        public ActionResult Index2() {
            ViewBag.Message = "Welcome to ASP.NET MVC!";
            return View("NotIndex");
        }

        public ActionResult Index3() {
            ViewBag.Message = "Welcome to ASP.NET MVC!";
            return View("~/Views/Example/Index.cshtml");
        }

        public ActionResult Message() {
            ViewBag.Message = "This is a partial view.";
            return PartialView();
        }

        public ActionResult PartialViewDemo() {
            return View();
        }
    }
}
