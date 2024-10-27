const express = require('express');
const router = express.Router();
const { customerRepo } = require('./db/entities');

router.put('/crm/customer', async (req, res) => {

    try {
        const {
        customerId,
        fullName,         
        contactEmail,       
        primaryPhone,       
        location   
        } = req.body;

        c = await customerRepo.findByPk(customerId);
        console.log(c);
        if(!c) {
            const newUser =  await customerRepo.create({
                id: customerId,
                fullName,         
                contactEmail,       
                primaryPhone,       
                location   
            })

            res.status(201).json(newUser);
        } else {
            const [updatedRows] = await customerRepo.update(
                {
                    fullName,         
                    contactEmail,       
                    primaryPhone,       
                    location   
                }, {where: {id: customerId}}
            )

            if (updatedRows) {
                res.status(200).json(
                    {
                        customerId,
                        fullName,         
                        contactEmail,       
                        primaryPhone,       
                        location   
                    }
                );
            } else {
                console.log('Customer not found');
                res.status(404).json({ error: 'Customer not found' });
            }
        }
    } catch(err) {
        res.status(400).json({ error: err.message });
    }
})

router.delete('/crm/customer/:id', async (req, res) => {

    try {
        const customer = await customerRepo.findByPk(req.params.id);
        if (!customer) {
            throw new Error('Customer not found');
        }
        await customer.destroy();
        res.status(204);
    } catch(err) {
        res.status(400).json({ error: err.message });
    }
})

router.get('/crm/customer/one-by-id/:id', async (req, res) => {

    try {
        const customer = await customerRepo.findByPk(req.params.id);
        if (!customer) {
            throw new Error('Customer not found');
        }
        
        res.status(200).json(customer);
    } catch(err) {
        res.status(400).json({ error: err.message });
    }
})

module.exports.router = router;