using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Models.Entities
{
    public class ItemsContainer<T>
    {
        public int TotalCount { get; set; }
        public List<T> Items { get; set; }

        public ItemsContainer()
            : this(Enumerable.Empty<T>()) { }

        public ItemsContainer(IEnumerable<T> items)
            : this(items, items.Count()) { }

        public ItemsContainer(IEnumerable<T> items, int totalCount)
        {
            Items = new List<T>(items);
            TotalCount = totalCount;
        }
    }
}
