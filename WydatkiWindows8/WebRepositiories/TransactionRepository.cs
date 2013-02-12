using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Models.Entities;
using WebRepositiories.Interfaces;

namespace WebRepositiories
{
    public class TransactionRepository: IRepository<Transaction>
    {
        public int CreateAllFromList(ItemsContainer<ModelBase> items)
        {
            throw new NotImplementedException();
        }

        public int Create(Transaction item)
        {
            throw new NotImplementedException();
        }

        public Transaction Read(int id)
        {
            throw new NotImplementedException();
        }

        public ObservableCollection<Transaction> ReadAll()
        {
            ObservableCollection<Transaction> items = new ObservableCollection<Transaction>();

            items.Add(new Transaction()
            {
                Id = 1,
                 Value=100.11,
                  Date=new DateTime()

            });

            items.Add(new Transaction()
            {
                Id = 2,
                Value = 200.12,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 3,
                Value = 300.13,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 4,
                Value = 400.14,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 5,
                Value = 500.15,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 1,
                Value = 100.11,
                Date = new DateTime()

            });

            items.Add(new Transaction()
            {
                Id = 2,
                Value = 200.12,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 3,
                Value = 300.13,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 4,
                Value = 400.14,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 5,
                Value = 500.15,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 1,
                Value = 100.11,
                Date = new DateTime()

            });

            items.Add(new Transaction()
            {
                Id = 2,
                Value = 200.12,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 3,
                Value = 300.13,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 4,
                Value = 400.14,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 5,
                Value = 500.15,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 1,
                Value = 100.11,
                Date = new DateTime()

            });

            items.Add(new Transaction()
            {
                Id = 2,
                Value = 200.12,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 3,
                Value = 300.13,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 4,
                Value = 400.14,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 5,
                Value = 500.15,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 1,
                Value = 100.11,
                Date = new DateTime()

            });

            items.Add(new Transaction()
            {
                Id = 2,
                Value = 200.12,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 3,
                Value = 300.13,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 4,
                Value = 400.14,
                Date = new DateTime()
            });

            items.Add(new Transaction()
            {
                Id = 5,
                Value = 500.15,
                Date = new DateTime()
            });


            return items;
        }

        public Transaction Update(Transaction item)
        {
            throw new NotImplementedException();
        }

        public bool Delete(List<ModelBase> ids)
        {
            throw new NotImplementedException();
        }
    }
}
