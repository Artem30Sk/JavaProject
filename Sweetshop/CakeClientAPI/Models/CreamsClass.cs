using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using CakeClientAPI.Entities;
namespace CakeClientAPI.Models
{
    public class CreamsClass
    {
        public CreamsClass(Creams creams)
        {
            Id = creams.Id;
            Title = creams.Title;
        }
        public int Id { get; set; }
        public string Title { get; set; }
    }
}