using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Models.Entities
{
    public class Project: ModelBase
    {
        public string Name { get; set; }
        public bool IsActive { get; set; }
        public bool IsVisibleForAll { get; set; }
    }
}
