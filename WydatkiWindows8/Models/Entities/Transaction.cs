using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Models.Entities
{
    public class Transaction : ModelBase
    {
        public int? AccMinus { get; set; }
        public int? AccPlus { get; set; }
        public DateTime Date { get; set; }
        public string Note { get; set; }
        public double Value { get; set; }
        public Category CategoryID { get; set; }
        public int? ProjectId { get; set; }
    }
}
