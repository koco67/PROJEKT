describe('frontend test', () => {
  it('index page to product pag', () => {

    cy.visit('http://localhost:63342/PROJEKT/frontend/src/main/resources/templates/index.html')

    cy.contains('Pikachu Card').click()

    cy.url().should('include', '/productpage')
  })
})