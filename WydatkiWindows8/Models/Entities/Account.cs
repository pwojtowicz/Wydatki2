using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Models.Entities
{
    public class Account : ModelBase
    {
        public string Name { get; set; }
        public double Balance { get; set; }
        public bool IsActive { get; set; }
        public bool IsVisibleForAll { get; set; }
        public bool IsSumInGlobalBalance { get; set; }
        public DateTime LastActionDate { get; set; }
        public byte ImageIndex { get; set; }
    }
}
