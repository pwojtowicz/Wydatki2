using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using Models.Entities;
using WebRepositiories;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// The Blank Page item template is documented at http://go.microsoft.com/fwlink/?LinkId=234238

namespace Wydatki
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class MainPage : Page
    {
      
        public MainPage()
        {
            this.InitializeComponent();

            AccountSource.Source = new AccountRepository().ReadAll();

            var tmp=new TransactionRepository().ReadAll();

            var primaryGroup = new TransactionsGroup() { Name = "Test", Items = tmp };

            var moreGroup = new TransactionsGroup() { Name = "Download More", Items = new ObservableCollection<Transaction>(){ new Transaction()} };

            TransactionsSource.Source = new ObservableCollection<TransactionsGroup>() { primaryGroup, moreGroup };
        }

        /// <summary>
        /// Invoked when this page is about to be displayed in a Frame.
        /// </summary>
        /// <param name="e">Event data that describes how this page was reached.  The Parameter
        /// property is typically used to configure the page.</param>
        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
        }
    }
}
