using CakeClientAPI.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CakeClientAPI.Models
{
    public class RecipesPicture
    {
        public RecipesPicture(Recipe recipe)
        {
            Id = recipe.Id;
            IdUser = recipe.IdUser;
            Title = recipe.Title;
            Description = recipe.Description;
            TypeCrust1 = recipe.TypeCrust1;
            TypeCrust2 = recipe.TypeCrust2;
            TypeCrust3 = recipe.TypeCrust3;
            TypeFilling1 = recipe.TypeFilling1;
            TypeFilling2 =recipe.TypeFilling2;
            TypeFilling3 = recipe.TypeFilling3;
            TypeCream1 = recipe.TypeCream1;
            TypeCream2 = recipe.TypeCream2;
            TypeCream3 = recipe.TypeCream3;
            TypeAddition1 = recipe.TypeAddition1;
            TypeAddition2 = recipe.TypeAddition2;
            TypeAddition3 = recipe.TypeAddition3;
            Cost = (decimal)recipe.Cost;
            Photo = recipe.Photo;


        }
        public int Id { get; set; }
        public int IdUser { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public Nullable<int>  TypeCrust1 { get; set; }
        public Nullable<int> TypeCrust2 { get; set; }
        public Nullable<int> TypeCrust3 { get; set; }
        public Nullable<int> TypeFilling1 { get; set; }
        public Nullable<int> TypeFilling2 { get; set; }
        public Nullable<int> TypeFilling3 { get; set; }
        public Nullable<int> TypeCream1 { get; set; }
        public Nullable<int> TypeCream2 { get; set; }
        public Nullable<int> TypeCream3 { get; set; }
        public Nullable<int> TypeAddition1 { get; set; }
        public Nullable<int> TypeAddition2 { get; set; }
        public Nullable<int> TypeAddition3 { get; set; }
        public decimal Cost { get; set; }

        public byte[] Photo { get; set; }
        
    }
}