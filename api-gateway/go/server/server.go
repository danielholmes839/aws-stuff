package server

import (
	"encoding/json"
	"net/http"
	"time"

	"github.com/go-chi/chi/middleware"
	"github.com/go-chi/chi/v5"
)

type response struct {
	Message string `json:"message"`
	Count   int    `json:"count"`
}

type server struct {
	router  chi.Router
	message string
	counter int
}

func New(message string) http.Handler {
	s := &server{
		router:  chi.NewRouter(),
		message: message,
		counter: 0,
	}

	s.routes()
	return s.router
}

func (s *server) routes() {
	s.router.Use(middleware.RequestID)
	s.router.Use(middleware.RealIP)
	s.router.Use(middleware.Logger)
	s.router.Use(middleware.Recoverer)

	s.router.Use(middleware.Timeout(5 * time.Second))

	s.router.Get("/", s.read())
	s.router.Get("/increment", s.increment())
}

func (s *server) read() http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		s.reply(w)
	}
}

func (s *server) increment() http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		s.counter++
		s.reply(w)
	}
}

func (s *server) reply(w http.ResponseWriter) {
	bytes, _ := json.Marshal(response{
		Message: s.message,
		Count:   s.counter,
	})

	w.Write(bytes)
}
