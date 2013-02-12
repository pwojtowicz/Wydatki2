using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Models.Entities
{
    public class Attribute : ModelBase
    {
        public string Name { get; set; }
        public bool IsActive { get; set; }
        public string DefaultValue { get; set; }
        public int Type { get; set; }
        public string DataSource { get; set; }
        public string Value { get; set; }
    }
}
