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
    public class AccountRepository: IRepository<Account>
    {
        public int CreateAllFromList(ItemsContainer<ModelBase> items)
        {
            throw new NotImplementedException();
        }

        public int Create(Account item)
        {
            throw new NotImplementedException();
        }

        public Account Read(int id)
        {
            throw new NotImplementedException();
        }

        public ObservableCollection<Account> ReadAll()
        {
            ObservableCollection<Account> accounts = new ObservableCollection<Account>();

            accounts.Add(new Account()
            {
                Id = 1,
                Name = "Test1",
                Balance = 100.10,
                ImageIndex = 1,
                IsActive = true,
                IsSumInGlobalBalance = true,
                IsVisibleForAll = true,
                LastActionDate = new DateTime()
            });

            accounts.Add(new Account()
            {
                Id = 2,
                Name = "Test2",
                Balance = 200.20,
                ImageIndex = 1,
                IsActive = true,
                IsSumInGlobalBalance = false,
                IsVisibleForAll = true,
                LastActionDate = new DateTime()
            });

            accounts.Add(new Account()
            {
                Id = 3,
                Name = "Test3",
                Balance = 300.30,
                ImageIndex = 1,
                IsActive = false,
                IsSumInGlobalBalance = true,
                IsVisibleForAll = true,
                LastActionDate = new DateTime()
            });

            accounts.Add(new Account()
            {
                Id = 3,
                Name = "Test3",
                Balance = 300.30,
                ImageIndex = 1,
                IsActive = false,
                IsSumInGlobalBalance = true,
                IsVisibleForAll = true,
                LastActionDate = new DateTime()
            });

            accounts.Add(new Account()
            {
                Id = 3,
                Name = "Test3",
                Balance = 300.30,
                ImageIndex = 1,
                IsActive = false,
                IsSumInGlobalBalance = true,
                IsVisibleForAll = true,
                LastActionDate = new DateTime()
            });

            accounts.Add(new Account()
            {
                Id = 3,
                Name = "Test3",
                Balance = 300.30,
                ImageIndex = 1,
                IsActive = false,
                IsSumInGlobalBalance = true,
                IsVisibleForAll = true,
                LastActionDate = new DateTime()
            });

            accounts.Add(new Account()
            {
                Id = 3,
                Name = "Test3",
                Balance = 300.30,
                ImageIndex = 1,
                IsActive = false,
                IsSumInGlobalBalance = true,
                IsVisibleForAll = true,
                LastActionDate = new DateTime()
            });

            accounts.Add(new Account()
            {
                Id = 3,
                Name = "Test3",
                Balance = 300.30,
                ImageIndex = 1,
                IsActive = false,
                IsSumInGlobalBalance = true,
                IsVisibleForAll = true,
                LastActionDate = new DateTime()
            });

            accounts.Add(new Account()
            {
                Id = 3,
                Name = "Test3",
                Balance = 300.30,
                ImageIndex = 1,
                IsActive = false,
                IsSumInGlobalBalance = true,
                IsVisibleForAll = true,
                LastActionDate = new DateTime()
            });

            return accounts;
        }

        public Account Update(Account item)
        {
            throw new NotImplementedException();
        }

        public bool Delete(List<ModelBase> ids)
        {
            throw new NotImplementedException();
        }
    }
}
