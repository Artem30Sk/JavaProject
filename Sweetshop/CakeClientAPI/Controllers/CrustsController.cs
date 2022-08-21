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
    public class CrustsController : ApiController
    {
        private cakeClientEntities db = new cakeClientEntities();

        // GET: api/Crusts
        [ResponseType(typeof(List<CrustsClass>))]
        public IHttpActionResult GetCrusts()
        {
            return Ok(db.Crusts.ToList().ConvertAll(p=>new CrustsClass(p)));
        }

        // GET: api/Crusts/5
        [ResponseType(typeof(Crusts))]
        public IHttpActionResult GetCrusts(int id)
        {
            Crusts crusts = db.Crusts.Find(id);
            if (crusts == null)
            {
                return NotFound();
            }

            return Ok(crusts);
        }

        // PUT: api/Crusts/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutCrusts(int id, Crusts crusts)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != crusts.Id)
            {
                return BadRequest();
            }

            db.Entry(crusts).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CrustsExists(id))
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

        // POST: api/Crusts
        [ResponseType(typeof(Crusts))]
        public IHttpActionResult PostCrusts(Crusts crusts)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Crusts.Add(crusts);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (CrustsExists(crusts.Id))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = crusts.Id }, crusts);
        }

        // DELETE: api/Crusts/5
        [ResponseType(typeof(Crusts))]
        public IHttpActionResult DeleteCrusts(int id)
        {
            Crusts crusts = db.Crusts.Find(id);
            if (crusts == null)
            {
                return NotFound();
            }

            db.Crusts.Remove(crusts);
            db.SaveChanges();

            return Ok(crusts);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool CrustsExists(int id)
        {
            return db.Crusts.Count(e => e.Id == id) > 0;
        }
    }
}