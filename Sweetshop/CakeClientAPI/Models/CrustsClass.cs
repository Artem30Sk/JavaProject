using CakeClientAPI.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CakeClientAPI.Models
{
    public class CrustsClass
    {
        public CrustsClass(Crusts crusts)
        {
            Id = crusts.Id;
            Title = crusts.Title;
        }
        public int Id { get; set; }
        public string Title { get; set; }
    }
}