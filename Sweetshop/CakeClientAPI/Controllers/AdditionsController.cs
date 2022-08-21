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
    public class AdditionsController : ApiController
    {
        private cakeClientEntities db = new cakeClientEntities();

        // GET: api/Additions
        [ResponseType(typeof(List<AdditionsClass>))]
        public IHttpActionResult GetAdditions()
        {
            return Ok(db.Additions.ToList().ConvertAll(p=>new AdditionsClass(p)));
        }

        // GET: api/Additions/5
        [ResponseType(typeof(Additions))]
        public IHttpActionResult GetAdditions(int id)
        {
            Additions additions = db.Additions.Find(id);
            if (additions == null)
            {
                return NotFound();
            }

            return Ok(additions);
        }

        // PUT: api/Additions/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutAdditions(int id, Additions additions)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != additions.Id)
            {
                return BadRequest();
            }

            db.Entry(additions).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!AdditionsExists(id))
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

        // POST: api/Additions
        [ResponseType(typeof(Additions))]
        public IHttpActionResult PostAdditions(Additions additions)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Additions.Add(additions);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (AdditionsExists(additions.Id))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = additions.Id }, additions);
        }

        // DELETE: api/Additions/5
        [ResponseType(typeof(Additions))]
        public IHttpActionResult DeleteAdditions(int id)
        {
            Additions additions = db.Additions.Find(id);
            if (additions == null)
            {
                return NotFound();
            }

            db.Additions.Remove(additions);
            db.SaveChanges();

            return Ok(additions);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool AdditionsExists(int id)
        {
            return db.Additions.Count(e => e.Id == id) > 0;
        }
    }
}