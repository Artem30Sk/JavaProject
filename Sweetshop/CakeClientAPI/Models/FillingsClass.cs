using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using CakeClientAPI.Entities;
namespace CakeClientAPI.Models
{
    public class FillingsClass
    {
        public FillingsClass(Fillings fillings)
        {
            Id = fillings.Id;
            Title = fillings.Title;
        }
        public int Id { get; set; }
        public string Title { get; set; }
    }
}