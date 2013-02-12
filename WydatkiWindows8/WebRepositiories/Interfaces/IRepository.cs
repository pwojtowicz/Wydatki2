using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Models.Entities;

namespace WebRepositiories.Interfaces
{
    public interface IRepository<T>
    {
        int CreateAllFromList(ItemsContainer<ModelBase> items);

	    int Create(T item);

        T Read(int id);

        ObservableCollection<T> ReadAll();

	    T Update(T item);

        bool Delete(List<ModelBase> ids);

        

    }
}
