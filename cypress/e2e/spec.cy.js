describe('frontend test', () => {
  it('index page to product pag', () => {
    //url changes all the time and needs to be corrected here when html file is opend
    cy.visit('http://localhost:63342/PROJEKT/frontend/src/main/resources/templates/index.html?_ijt=p9tdsdrbgpi9vb4tio1ucu7fjg&_ij_reload=RELOAD_ON_SAVE')

    cy.contains('Pikachu Card').click()

    cy.url().should('include', '/productpage')
  })
})