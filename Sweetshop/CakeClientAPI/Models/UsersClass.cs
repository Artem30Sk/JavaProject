using CakeClientAPI.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CakeClientAPI.Models
{
    public class UsersClass
    {
        public UsersClass(User user)
        {
            Id = user.Id;
            Login = user.Login;
            Password = user.Password;
            Phone = user.Phone;
            Address = user.Address;
            
        }
        public int Id { get; set; }
        public string Login { get; set; }
        public string Password { get; set; }
        public string Phone { get; set; }
        public string Address { get; set; }
       
    }
}