using CakeClientAPI.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CakeClientAPI.Models
{
    public class OrdersClass
    {
        public OrdersClass(Orders orders)
        {
            Id = orders.Id;
            IdUser = orders.IdUser;
            IdRecipe = orders.IdRecipe;
            Weight = orders.Weight;
            Cost = orders.Cost;
            Address = orders.Address;
            Data = orders.Data;
        }
        public int Id { get; set; }
        public int IdUser { get; set; }
        public int IdRecipe { get; set; }
        public int Weight { get; set; }
        public decimal Cost { get; set; }
        public string Address { get; set; }
        public System.DateTime Data { get; set; }
    }
}