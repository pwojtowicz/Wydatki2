using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Models.Entities
{
    public class StartContainer
    {
        public List<Account> Accounts { get; set; }
        public List<Entities.Attribute> Parameters { get; set; }
        public List<Project> Projects { get; set; }
        public List<Category> Categories { get; set; }
        public List<User> Users { get; set; }
    }
}
