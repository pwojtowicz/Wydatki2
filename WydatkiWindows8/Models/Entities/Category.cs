using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Models.Entities
{
    public class Category : ModelBase
    {
        public int? ParentId { get; set; }
        public string Name { get; set; }
        public bool IsActive { get; set; }
        public List<Attribute> Attributes { get; set; }
        public string RN { get; set; }
        public bool IsPositive { get; set; }
    }
}
