using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Models.Entities
{
    public class User : ModelBase
    {
        public string Login { get; set; }
        public string Password { get; set; }
    }
}
