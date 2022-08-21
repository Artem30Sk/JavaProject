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
    public class CreamsController : ApiController
    {
        private cakeClientEntities db = new cakeClientEntities();

        // GET: api/Creams
        [ResponseType(typeof(List<CreamsClass>))]
        public IHttpActionResult GetCreams()
        {
            return Ok(db.Creams.ToList().ConvertAll(p=>new CreamsClass(p)));
        }

        // GET: api/Creams/5
        [ResponseType(typeof(Creams))]
        public IHttpActionResult GetCreams(int id)
        {
            Creams creams = db.Creams.Find(id);
            if (creams == null)
            {
                return NotFound();
            }

            return Ok(creams);
        }

        // PUT: api/Creams/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutCreams(int id, Creams creams)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != creams.Id)
            {
                return BadRequest();
            }

            db.Entry(creams).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CreamsExists(id))
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

        // POST: api/Creams
        [ResponseType(typeof(Creams))]
        public IHttpActionResult PostCreams(Creams creams)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Creams.Add(creams);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (CreamsExists(creams.Id))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = creams.Id }, creams);
        }

        // DELETE: api/Creams/5
        [ResponseType(typeof(Creams))]
        public IHttpActionResult DeleteCreams(int id)
        {
            Creams creams = db.Creams.Find(id);
            if (creams == null)
            {
                return NotFound();
            }

            db.Creams.Remove(creams);
            db.SaveChanges();

            return Ok(creams);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool CreamsExists(int id)
        {
            return db.Creams.Count(e => e.Id == id) > 0;
        }
    }
}