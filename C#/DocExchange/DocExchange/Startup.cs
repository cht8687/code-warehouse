using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(DocExchange.Startup))]
namespace DocExchange
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
