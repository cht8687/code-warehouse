using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace TrackViewAPI.Controllers.Api
{
    public class NewsController : ApiController
    {
        // GET: api/News
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET: api/News/5
        public string Get(int id)
        {
            return "value";
        }

        // POST: api/News
        public void Post([FromBody]string value)
        {
        }

        // PUT: api/News/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE: api/News/5
        public void Delete(int id)
        {
        }
    }
}
