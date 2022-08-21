using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using CakeClientAPI.Entities;
namespace CakeClientAPI.Models
{
    public class AdditionsClass
    {
        public AdditionsClass(Additions additions)
        {
            Id = additions.Id;
            Title = additions.Title;
        }
        public int Id { get; set; }
        public string Title { get; set; }
    }
}