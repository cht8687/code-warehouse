using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace DocExchange.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult About()
        {
            ViewBag.Message = "Your application description page.";

            return View();
        }

        public ActionResult Contact()
        {
            ViewBag.Message = "Your contact page.";

            return View();
        }

        public ActionResult Step1()
        {
            ViewBag.Message = "STEP 1";

            return View();
        }

        public ActionResult Basic()
        {
            return View();
        }

        public ActionResult Advanced(int id)
        {
            var person = new DocExchange.Models.Person
            {
                FirstName = "Robert",
                LastName = "Chang" 
            };
            return View(person);
        }
    }
}