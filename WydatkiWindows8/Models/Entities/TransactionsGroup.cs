using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Models.Entities
{
    public class TransactionsGroup
    {

        public string Name { get; set; }
        public ObservableCollection<Transaction> Items { get; set; }
    }
}
