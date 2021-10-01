import React from 'react'
import { Route, Switch } from 'react-router-dom'
import { QueryClient, QueryClientProvider } from 'react-query'
import { ReactQueryDevtools } from 'react-query/devtools'

import Footer from '@/layout/Footer'
import Navbar from '@/layout/Navbar'

import Home from '@/screens/Home'
import NotFound from '@/screens/NotFound'
import ScreenCategoryList from '@/screens/category/List'
import ScreenCategoryForm from '@/screens/category/Form'
import ScreenAuthorList from '@/screens/author/List'
import ScreenAuthorForm from '@/screens/author/Form'
import ScreenBookList from '@/screens/book/List'
import ScreenBookForm from '@/screens/book/Form'
import ScreenBookDetail from '@/screens/book/Detail'
import ScreenLogin from '@/screens/auth/Login'
import ScreenJoin from '@/screens/auth/Join'

function App() {
  const queryClient = new QueryClient()

  return (
    <>
      <header>
        <Navbar />
      </header>
      <main className="container flex-grow p-4 mx-auto">
        <QueryClientProvider client={queryClient}>
          <Switch>
            <Route exact path="/">
              <Home />
            </Route>
            <Route exact path="/category">
              <ScreenCategoryList />
            </Route>
            <Route path="/category/edit/:id">
              <ScreenCategoryForm />
            </Route>
            <Route path="/category/create">
              <ScreenCategoryForm />
            </Route>
            <Route exact path="/author">
              <ScreenAuthorList />
            </Route>
            <Route path="/author/edit/:id">
              <ScreenAuthorForm />
            </Route>
            <Route path="/author/create">
              <ScreenAuthorForm />
            </Route>
            <Route exact path="/book">
              <ScreenBookList />
            </Route>
            <Route path="/book/edit/:id">
              <ScreenBookForm />
            </Route>
            <Route path="/book/detail/:id">
              <ScreenBookDetail />
            </Route>
            <Route path="/book/create">
              <ScreenBookForm />
            </Route>
            <Route path="/login">
              <ScreenLogin />
            </Route>
            <Route path="/join">
              <ScreenJoin />
            </Route>
            <Route component={NotFound} />
          </Switch>
          <ReactQueryDevtools initialIsOpen={false} />
        </QueryClientProvider>
      </main>
      <Footer />
    </>
  )
}

export default App
