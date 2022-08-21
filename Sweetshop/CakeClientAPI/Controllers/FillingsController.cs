using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using CakeClientAPI.Entities;
using CakeClientAPI.Models;

namespace CakeClientAPI.Controllers
{
    public class FillingsController : ApiController
    {
        private cakeClientEntities db = new cakeClientEntities();

        // GET: api/Fillings
        [ResponseType(typeof(List<FillingsClass>))]
        public IHttpActionResult GetFillings()
        {
            return Ok(db.Fillings.ToList().ConvertAll(p=>new FillingsClass(p)));
        }

        // GET: api/Fillings/5
        [ResponseType(typeof(Fillings))]
        public IHttpActionResult GetFillings(int id)
        {
            Fillings fillings = db.Fillings.Find(id);
            if (fillings == null)
            {
                return NotFound();
            }

            return Ok(fillings);
        }

        // PUT: api/Fillings/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutFillings(int id, Fillings fillings)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != fillings.Id)
            {
                return BadRequest();
            }

            db.Entry(fillings).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!FillingsExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Fillings
        [ResponseType(typeof(Fillings))]
        public IHttpActionResult PostFillings(Fillings fillings)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Fillings.Add(fillings);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (FillingsExists(fillings.Id))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = fillings.Id }, fillings);
        }

        // DELETE: api/Fillings/5
        [ResponseType(typeof(Fillings))]
        public IHttpActionResult DeleteFillings(int id)
        {
            Fillings fillings = db.Fillings.Find(id);
            if (fillings == null)
            {
                return NotFound();
            }

            db.Fillings.Remove(fillings);
            db.SaveChanges();

            return Ok(fillings);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool FillingsExists(int id)
        {
            return db.Fillings.Count(e => e.Id == id) > 0;
        }
    }
}